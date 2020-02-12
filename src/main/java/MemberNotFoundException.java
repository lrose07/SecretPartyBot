class MemberNotFoundException extends Exception {
    private String notFoundName;

    MemberNotFoundException(String userName) {
        notFoundName = userName;
    }

    String getNotFoundName() {
        return notFoundName;
    }
}
