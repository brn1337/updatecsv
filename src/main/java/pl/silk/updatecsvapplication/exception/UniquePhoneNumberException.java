package pl.silk.updatecsvapplication.exception;

import lombok.Getter;

import java.sql.SQLException;

@Getter
public class UniquePhoneNumberException extends SQLException {

    private SQLException throwable;

    public UniquePhoneNumberException(SQLException throwables) {
        this.throwable = throwables;
    }
}
