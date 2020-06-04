public record Template(String name, String address) {
    @Override
    public String toString() {
        return name + ';' + address;
    }
}
