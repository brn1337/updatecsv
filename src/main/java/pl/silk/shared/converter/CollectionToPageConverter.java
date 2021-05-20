package pl.silk.shared.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.comparators.NullComparator;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ClassUtils;
import pl.silk.updatecsvapplication.exception.ProcessingException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionToPageConverter {

    public static <T> Page<T> generatePage(Pageable pageable, Collection<T> data) {
        if (data.isEmpty()) {
            return Page.empty();
        }

        final T object = data.iterator().next();
        final List<T> sortedData = pageable.getSort().stream()
                .filter(isReadable(object))
                .filter(isComparable(object))
                .map(generateComparator())
                .reduce(Comparator::thenComparing)
                .map(objectComparator -> data.stream().sorted(objectComparator).collect(Collectors.toList()))
                .orElse(new ArrayList<>(data));

        final PagedListHolder<T> dataHolder = new PagedListHolder<>(sortedData);
        dataHolder.setPage(pageable.getPageNumber());
        dataHolder.setPageSize(pageable.getPageSize());

        return new PageImpl<>(dataHolder.getPageList(), pageable, sortedData.size());
    }

    private static <T> Predicate<Sort.Order> isReadable(T object) {
        return o -> {
            final String propertyName = o.getProperty();
            final boolean readable = PropertyUtils.isReadable(object, propertyName);
            if (!readable) {
                log.info("Field '{}' is not readable on class '{}'", propertyName, object.getClass());
            }
            return readable;
        };
    }

    private static <T> Predicate<Sort.Order> isComparable(T object) {
        return o -> {
            try {
                final String propertyName = o.getProperty();
                final Class<?> propertyType = PropertyUtils.getPropertyType(object, propertyName);
                final boolean isComparable = Comparable.class.isAssignableFrom(propertyType) || ClassUtils.isPrimitiveOrWrapper(propertyType);
                if (!isComparable) {
                    log.info("File '{}' is not comparable on class '{}'", propertyName, object.getClass());
                }
                return isComparable;
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new ProcessingException("Sorting error", e);
            }
        };
    }

    private static Function<Sort.Order, Comparator<Object>> generateComparator() {
        return sortDefinition -> {
            BeanComparator<Object> beanComparator = new BeanComparator<>(sortDefinition.getProperty(), new NullComparator(true));
            if (sortDefinition.isDescending()) {
                return beanComparator.reversed();
            }
            return beanComparator;
        };
    }
}
