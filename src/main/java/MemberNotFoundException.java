class MemberNotFoundException extends Exception {
    private final String notFoundName;

    MemberNotFoundException(String userName) {
        notFoundName = userName;
    }

    String getNotFoundName() {
        return notFoundName;
    }
}
