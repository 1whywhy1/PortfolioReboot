package com.thisisivan;

public enum UserType
{
    User(0),
    Moderator(1),
    Admin(2);

    private final int id;
    UserType(int id) { this.id = id; }
    public int getValue() { return id; }

}
