package digital.and.sunshine.weather.model;

public record Radiation(
        double ghi,
        double dni,
        double dhi,
        double ghi_cs,
        double dni_cs,
        double dhi_cs) {
}