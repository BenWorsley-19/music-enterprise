package com.worsley.dao.exception;

/**
 * Exception thrown when an error occurs retrieving the data from the repository.
 */
public class RepositoryException extends Exception
{
    public RepositoryException(String message, Throwable cause)
    {
        super(message, cause);
    }
}