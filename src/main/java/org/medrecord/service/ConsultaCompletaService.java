package org.medrecord.service;

import org.medrecord.model.ConsultaMedica;
import org.medrecord.model.Prescripcion;
import org.medrecord.model.RegistroMedida;
import org.medrecord.model.RegistroProcedimiento;
import org.medrecord.dto.ConsultaMedicaDTO;
import org.medrecord.dto.PrescripcionDTO;
import org.medrecord.dto.MedidasFisiologicasDTO;
import org.medrecord.dto.ProcedimientoMedicoDTO;
import org.medrecord.config.DataBaseConfig;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class ConsultaCompletaService {
    private final ConsultaMedicaService consultaMedicaService;
    private final PrescripcionService prescripcionService;
    private final RegistroMedidaService registroMedidaService;
    private final RegistroProcedimientoService registroProcedimientoService;

    public ConsultaCompletaService(
            ConsultaMedicaService consultaMedicaService,
            PrescripcionService prescripcionService,
            RegistroMedidaService registroMedidaService,
            RegistroProcedimientoService registroProcedimientoService) {
        this.consultaMedicaService = consultaMedicaService;
        this.prescripcionService = prescripcionService;
        this.registroMedidaService = registroMedidaService;
        this.registroProcedimientoService = registroProcedimientoService;
    }

    /**
     * Crea consulta completa desde request del controller
     */
    public int createConsultaCompletaFromRequest(Map<String, Object> requestBody) throws SQLException {
        // Extraer consulta médica
        Map<String, Object> consultaMap = (Map<String, Object>) requestBody.get("consultaMedica");
        ConsultaMedica consultaMedica = mapToConsultaMedica(consultaMap);

        // Extraer prescripciones (obligatorias)
        List<Map<String, Object>> prescripcionesMap = (List<Map<String, Object>>) requestBody.get("prescripciones");
        List<Prescripcion> prescripciones = mapToPrescripciones(prescripcionesMap);

        // Extraer medidas (opcionales)
        List<RegistroMedida> medidasConsulta = null;
        if (requestBody.containsKey("medidasConsulta")) {
            List<Map<String, Object>> medidasMap = (List<Map<String, Object>>) requestBody.get("medidasConsulta");
            medidasConsulta = mapToRegistroMedidas(medidasMap);
        }

        // Extraer procedimientos (opcionales)
        List<RegistroProcedimiento> procedimientos = null;
        if (requestBody.containsKey("procedimientos")) {
            List<Map<String, Object>> procedimientosMap = (List<Map<String, Object>>) requestBody.get("procedimientos");
            procedimientos = mapToRegistroProcedimientos(procedimientosMap);
        }

        return createConsultaCompleta(consultaMedica, prescripciones, medidasConsulta, procedimientos);
    }

    /**
     * Actualiza consulta completa desde request del controller
     */
    public int updateConsultaCompletaFromRequest(int idConsulta, Map<String, Object> requestBody) throws SQLException {
        // Extraer consulta médica
        Map<String, Object> consultaMap = (Map<String, Object>) requestBody.get("consultaMedica");
        ConsultaMedica consultaMedica = mapToConsultaMedica(consultaMap);
        consultaMedica.setIdConsulta(idConsulta);

        // Extraer prescripciones (obligatorias)
        List<Map<String, Object>> prescripcionesMap = (List<Map<String, Object>>) requestBody.get("prescripciones");
        List<Prescripcion> prescripciones = mapToPrescripciones(prescripcionesMap);

        // Extraer medidas (opcionales)
        List<RegistroMedida> medidasConsulta = null;
        if (requestBody.containsKey("medidasConsulta")) {
            List<Map<String, Object>> medidasMap = (List<Map<String, Object>>) requestBody.get("medidasConsulta");
            medidasConsulta = mapToRegistroMedidas(medidasMap);
        }

        // Extraer procedimientos (opcionales)
        List<RegistroProcedimiento> procedimientos = null;
        if (requestBody.containsKey("procedimientos")) {
            List<Map<String, Object>> procedimientosMap = (List<Map<String, Object>>) requestBody.get("procedimientos");
            procedimientos = mapToRegistroProcedimientos(procedimientosMap);
        }

        return updateConsultaCompleta(consultaMedica, prescripciones, medidasConsulta, procedimientos);
    }

    // Métodos helper para mapear datos (movidos desde controller)
    private ConsultaMedica mapToConsultaMedica(Map<String, Object> map) {
        ConsultaMedica consulta = new ConsultaMedica();
        consulta.setIdUsuario((Integer) map.get("idUsuario"));
        consulta.setDiagnostico((String) map.get("diagnostico"));
        consulta.setDoctor((String) map.get("doctor"));
        consulta.setClinica((String) map.get("clinica"));
        consulta.setFechaConsulta(java.sql.Date.valueOf((String) map.get("fechaConsulta")));
        return consulta;
    }

    private List<Prescripcion> mapToPrescripciones(List<Map<String, Object>> mapList) {
        if (mapList == null) return null;

        List<Prescripcion> prescripciones = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            Prescripcion prescripcion = new Prescripcion();
            if (map.containsKey("idPrescripcion") && map.get("idPrescripcion") != null) {
                prescripcion.setIdPrescripcion((Integer) map.get("idPrescripcion"));
            }
            prescripcion.setNombreMedicamento((String) map.get("nombreMedicamento"));
            prescripcion.setPresentacionMedicamento((String) map.get("presentacionMedicamento"));
            prescripcion.setDosis((String) map.get("dosis"));
            prescripcion.setFrecuencia((String) map.get("frecuencia"));
            prescripcion.setDuracion((String) map.get("duracion"));
            prescripciones.add(prescripcion);
        }
        return prescripciones;
    }

    private List<RegistroMedida> mapToRegistroMedidas(List<Map<String, Object>> mapList) {
        if (mapList == null) return null;

        List<RegistroMedida> medidas = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            RegistroMedida medida = new RegistroMedida();
            if (map.containsKey("idRegistroMedida") && map.get("idRegistroMedida") != null) {
                medida.setIdRegistroMedida((Integer) map.get("idRegistroMedida"));
            }
            medida.setIdMedida((Integer) map.get("idMedida"));
            medida.setFechaRegistro(java.sql.Date.valueOf((String) map.get("fechaRegistro")));
            medida.setValorMedida((Double) map.get("valorMedida"));
            medida.setNotaAdicional((String) map.get("notaAdicional"));
            medidas.add(medida);
        }
        return medidas;
    }

    private List<RegistroProcedimiento> mapToRegistroProcedimientos(List<Map<String, Object>> mapList) {
        if (mapList == null) return null;

        List<RegistroProcedimiento> procedimientos = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            RegistroProcedimiento procedimiento = new RegistroProcedimiento();
            if (map.containsKey("idRegistroProcedimiento") && map.get("idRegistroProcedimiento") != null) {
                procedimiento.setIdRegistroProcedimiento((Integer) map.get("idRegistroProcedimiento"));
            }
            procedimiento.setIdProcedimiento((Integer) map.get("idProcedimiento"));
            procedimiento.setNotaAdicional((String) map.get("notaAdicional"));
            procedimientos.add(procedimiento);
        }
        return procedimientos;
    }

    /**
     * Crea una consulta médica completa con todas sus prescripciones, medidas y procedimientos
     * Todo se ejecuta en una sola transacción
     */
    public int createConsultaCompleta(
            ConsultaMedica consultaMedica,
            List<Prescripcion> prescripciones,
            List<RegistroMedida> medidasConsulta,
            List<RegistroProcedimiento> procedimientos) throws SQLException {

        Connection conn = null;
        try {
            conn = DataBaseConfig.getDataSource().getConnection();
            conn.setAutoCommit(false);

            // 1. Crear la consulta médica
            int idConsulta = consultaMedicaService.create(consultaMedica);

            // 2. Asignar el ID de consulta a todas las prescripciones y crearlas
            if (prescripciones != null && !prescripciones.isEmpty()) {
                for (Prescripcion prescripcion : prescripciones) {
                    prescripcion.setIdConsulta(idConsulta);
                    prescripcionService.create(prescripcion);
                }
            }

            // 3. Asignar el ID de consulta a todas las medidas y crearlas (opcional)
            if (medidasConsulta != null && !medidasConsulta.isEmpty()) {
                for (RegistroMedida medida : medidasConsulta) {
                    medida.setIdConsulta(idConsulta);
                    medida.setIdUsuario(null); // Asegurar que es null para medidas de consulta
                    registroMedidaService.createMedidaConsulta(medida);
                }
            }

            // 4. Asignar el ID de consulta a todos los procedimientos y crearlos (opcional)
            if (procedimientos != null && !procedimientos.isEmpty()) {
                for (RegistroProcedimiento procedimiento : procedimientos) {
                    procedimiento.setIdConsulta(idConsulta);
                    registroProcedimientoService.create(procedimiento);
                }
            }

            conn.commit();
            return idConsulta;

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("Error al crear la consulta completa: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    /**
     * Actualiza una consulta médica completa con todas sus prescripciones, medidas y procedimientos
     * Todo se ejecuta en una sola transacción
     */
    public int updateConsultaCompleta(
            ConsultaMedica consultaMedica,
            List<Prescripcion> prescripciones,
            List<RegistroMedida> medidasConsulta,
            List<RegistroProcedimiento> procedimientos) throws SQLException {

        Connection conn = null;
        try {
            conn = DataBaseConfig.getDataSource().getConnection();
            conn.setAutoCommit(false);

            int idConsulta = consultaMedica.getIdConsulta();

            // 1. Actualizar la consulta médica
            consultaMedicaService.update(consultaMedica);

            // 2. Actualizar prescripciones
            if (prescripciones != null && !prescripciones.isEmpty()) {
                for (Prescripcion prescripcion : prescripciones) {
                    prescripcion.setIdConsulta(idConsulta);
                    if (prescripcion.getIdPrescripcion() > 0) {
                        prescripcionService.update(prescripcion);
                    } else {
                        prescripcionService.create(prescripcion);
                    }
                }
            }

            // 3. Actualizar medidas de consulta (opcional)
            if (medidasConsulta != null && !medidasConsulta.isEmpty()) {
                for (RegistroMedida medida : medidasConsulta) {
                    medida.setIdConsulta(idConsulta);
                    medida.setIdUsuario(null);
                    if (medida.getIdRegistroMedida() > 0) {
                        registroMedidaService.update(medida);
                    } else {
                        registroMedidaService.createMedidaConsulta(medida);
                    }
                }
            }

            // 4. Actualizar procedimientos (opcional)
            if (procedimientos != null && !procedimientos.isEmpty()) {
                for (RegistroProcedimiento procedimiento : procedimientos) {
                    procedimiento.setIdConsulta(idConsulta);
                    if (procedimiento.getIdRegistroProcedimiento() > 0) {
                        registroProcedimientoService.update(procedimiento);
                    } else {
                        registroProcedimientoService.create(procedimiento);
                    }
                }
            }

            conn.commit();
            return idConsulta;

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("Error al actualizar la consulta completa: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    /**
     * Obtiene una consulta médica completa con todas sus prescripciones, medidas y procedimientos
     */
    public ConsultaMedicaDTO getConsultaCompleta(int idConsulta) throws SQLException {
        try {
            // Obtener prescripciones
            List<PrescripcionDTO> prescripciones = prescripcionService.getPrescripcionesByConsulta(idConsulta);

            // Obtener medidas (opcional)
            List<MedidasFisiologicasDTO> medidas = registroMedidaService.getMedidasConsulta(idConsulta);
            if (medidas == null) {
                medidas = new ArrayList<>();
            }

            // Obtener procedimientos (opcional)
            List<ProcedimientoMedicoDTO> procedimientos = registroProcedimientoService.getProcedimientosConsulta(idConsulta);
            if (procedimientos == null) {
                procedimientos = new ArrayList<>();
            }

            // Crear el DTO completo
            ConsultaMedicaDTO consultaCompleta = new ConsultaMedicaDTO();
            consultaCompleta.setPrescripcionDTOS(prescripciones);
            consultaCompleta.setMedidasFisiologicasDTOS(medidas);
            consultaCompleta.setProcedimientoMedicoDTOS(procedimientos);

            return consultaCompleta;

        } catch (Exception e) {
            throw new SQLException("Error al obtener la consulta completa: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene todas las consultas de un usuario con información básica
     */
    public List<ConsultaMedicaDTO> getConsultasByUsuario(int idUsuario) throws SQLException {
        return consultaMedicaService.getConsultasByUsuario(idUsuario);
    }

    // Métodos individuales para eliminaciones por separado
    public void deleteConsulta(int idConsulta) throws SQLException {
        consultaMedicaService.delete(idConsulta);
    }

    public void deletePrescripcion(int idPrescripcion) throws SQLException {
        prescripcionService.delete(idPrescripcion);
    }

    public void deleteRegistroMedida(int idRegistroMedida) throws SQLException {
        registroMedidaService.delete(idRegistroMedida);
    }

    public void deleteRegistroProcedimiento(int idRegistroProcedimiento) throws SQLException {
        registroProcedimientoService.delete(idRegistroProcedimiento);
    }
}