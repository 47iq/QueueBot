package data;

public enum Subject {
    OPD {
        @Override
        public String toString() {
            return "opd";
        }
    },
    PROGRAMMING {
        @Override
        public String toString() {
            return "programming";
        }
    };

    public static Subject forName(String subject) {
        switch (subject.trim().toLowerCase()) {
            case "opd" -> {
                return Subject.OPD;
            }
            case "programming" -> {
                return Subject.PROGRAMMING;
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }
    }
}
