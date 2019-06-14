package utfpr.edu.br.tcc.converter;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.data.convert.ThreeTenBackPortConverters.DateToInstantConverter;
import org.springframework.data.convert.ThreeTenBackPortConverters.DateToLocalDateConverter;
import org.springframework.data.convert.ThreeTenBackPortConverters.DateToLocalDateTimeConverter;
import org.springframework.data.convert.ThreeTenBackPortConverters.DateToLocalTimeConverter;
import org.springframework.data.convert.ThreeTenBackPortConverters.InstantToDateConverter;
import org.springframework.data.convert.ThreeTenBackPortConverters.LocalDateTimeToDateConverter;
import org.springframework.data.convert.ThreeTenBackPortConverters.LocalDateToDateConverter;
import org.springframework.data.convert.ThreeTenBackPortConverters.LocalTimeToDateConverter;
import org.springframework.data.convert.ThreeTenBackPortConverters.StringToZoneIdConverter;
import org.springframework.data.convert.ThreeTenBackPortConverters.ZoneIdToStringConverter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneId;


public class SeguroConverter {

    @Converter(autoApply = false)
    public static class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

        @Override
        public Date convertToDatabaseColumn(LocalDate date) {
            return LocalDateToDateConverter.INSTANCE.convert(date);
        }

        @Override
        public LocalDate convertToEntityAttribute(Date date) {
            return DateToLocalDateConverter.INSTANCE.convert(date);
        }
    }

//    @Converter(autoApply = false)
//    public static class LocalTimeConverter implements AttributeConverter<LocalTime, Date> {
//
//        @Override
//        public Date convertToDatabaseColumn(LocalTime time) {
//            return LocalTimeToDateConverter.INSTANCE.convert(time);
//        }
//
//        @Override
//        public LocalTime convertToEntityAttribute(Date date) {
//            return DateToLocalTimeConverter.INSTANCE.convert(date);
//        }
//    }

//    @Converter(autoApply = false)
//    public static class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {
//
//        @Override
//        public Date convertToDatabaseColumn(LocalDateTime date) {
//            return LocalDateTimeToDateConverter.INSTANCE.convert(date);
//        }
//
//        @Override
//        public LocalDateTime convertToEntityAttribute(Date date) {
//            return DateToLocalDateTimeConverter.INSTANCE.convert(date);
//        }
//    }

//    @Converter(autoApply = true)
//    public static class InstantConverter implements AttributeConverter<Instant, Date> {
//
//        @Override
//        public Date convertToDatabaseColumn(Instant instant) {
//            return InstantToDateConverter.INSTANCE.convert(instant);
//        }
//
//        @Override
//        public Instant convertToEntityAttribute(Date date) {
//            return DateToInstantConverter.INSTANCE.convert(date);
//        }
//    }
//
//    @Converter(autoApply = true)
//    public static class ZoneIdConverter implements AttributeConverter<ZoneId, String> {
//
//        @Override
//        public String convertToDatabaseColumn(ZoneId zoneId) {
//            return ZoneIdToStringConverter.INSTANCE.convert(zoneId);
//        }
//
//        @Override
//        public ZoneId convertToEntityAttribute(String zoneId) {
//            return StringToZoneIdConverter.INSTANCE.convert(zoneId);
//        }
//    }

}

