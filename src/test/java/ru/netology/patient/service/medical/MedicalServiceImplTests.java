package ru.netology.patient.service.medical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MedicalServiceImplTests {

    @Test
    public void checkBloodPressureTest() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.anyString()))
                .thenReturn(new PatientInfo("Пал", "Палыч",
                        LocalDate.of(1975, 5, 20),
                new HealthInfo(new BigDecimal("36.6"), new BloodPressure(125, 78))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        Mockito.doNothing().when(sendAlertService).send(Mockito.anyString());

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkBloodPressure(Mockito.anyString(), new BloodPressure(80, 78));

        Mockito.verify(sendAlertService, Mockito.atLeastOnce()).send(Mockito.anyString());
    }
    @Test
    public void checkTemperatureTest() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.anyString()))
                .thenReturn(new PatientInfo("Пал", "Палыч",
                        LocalDate.of(1975, 5, 20),
                        new HealthInfo(new BigDecimal("36.6"), new BloodPressure(125, 78))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        Mockito.doNothing().when(sendAlertService).send(Mockito.anyString());

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkTemperature(Mockito.anyString(), new BigDecimal(35.0));

        Mockito.verify(sendAlertService, Mockito.atLeastOnce()).send(Mockito.anyString());
    }

    @Test
    public void checkNormalHealthTest() {
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById(Mockito.anyString()))
                .thenReturn(new PatientInfo("Пал", "Палыч",
                        LocalDate.of(1975, 5, 20),
                        new HealthInfo(new BigDecimal("36.6"), new BloodPressure(125, 78))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        Mockito.doNothing().when(sendAlertService).send(Mockito.anyString());

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        medicalService.checkBloodPressure(Mockito.anyString(), new BloodPressure(125, 78));
        medicalService.checkTemperature(Mockito.anyString(), new BigDecimal(36.0));

        Mockito.verify(sendAlertService, Mockito.never()).send(Mockito.anyString());
    }
}
