package org.medrecord.service;

import org.medrecord.model.RegistroMedida;
import org.medrecord.dto.MedidasFisiologicasDTO;
import org.medrecord.repository.RegistroMedidaRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MedidasFisiologicasService {
    private final RegistroMedidaRepository registroMedidaRepository;

    public MedidasFisiologicasService(RegistroMedidaRepository registroMedidaRepository) {
        this.registroMedidaRepository = registroMedidaRepository;
    }

    /**
     * Actualiza registro de medida desde request del controller
     */
    public int updateRegistroMedidaFromRequest(int idRegistroMedida, Map<String, Object> requestBody) throws SQLException {
        RegistroMedida medida = mapToRegistroMedida(requestBody);
        medida.setIdRegistroMedida(idRegistroMedida);
        return updateRegistroMedida(medida);
    }

    // Método helper movido desde controller
    private RegistroMedida mapToRegistroMedida(Map<String, Object> map) {
        RegistroMedida medida = new RegistroMedida();
        medida.setFechaRegistro(java.sql.Date.valueOf((String) map.get("fechaRegistro")));
        medida.setValorMedida((Double) map.get("valorMedida"));
        medida.setNotaAdicional((String) map.get("notaAdicional"));
        return medida;
    }

    public int createMedidaPersonal(RegistroMedida registroMedida) throws SQLException {
        validateMedidaPersonal(registroMedida);
        return registroMedidaRepository.saveRegistroMedidaPersonal(registroMedida);
    }

    public List<MedidasFisiologicasDTO> getAllMedidasUsuario(int idUsuario) throws SQLException {
        if (idUsuario <= 0) {
            throw new IllegalArgumentException("El ID del usuario debe ser mayor a 0");
        }
        return registroMedidaRepository.findAllMedidasUsuario(idUsuario);
    }

    public int updateRegistroMedida(RegistroMedida registroMedida) throws SQLException {
        validateRegistroMedida(registroMedida);
        if (registroMedida.getIdRegistroMedida() <= 0) {
            throw new IllegalArgumentException("El ID del registro de medida debe ser mayor a 0 para actualizar");
        }
        return registroMedidaRepository.updateRegistroMedida(registroMedida);
    }

    public void deleteRegistroMedida(int idRegistroMedida) throws SQLException {
        if (idRegistroMedida <= 0) {
            throw new IllegalArgumentException("El ID del registro de medida debe ser mayor a 0");
        }
        registroMedidaRepository.deleteRegistroMedida(idRegistroMedida);
    }

    private void validateRegistroMedida(RegistroMedida registroMedida) {
        if (registroMedida == null) {
            throw new IllegalArgumentException("El registro de medida no puede ser nulo");
        }
        if (registroMedida.getFechaRegistro() == null) {
            throw new IllegalArgumentException("La fecha de registro es obligatoria");
        }
        if (registroMedida.getValorMedida() < 0) {
            throw new IllegalArgumentException("El valor de la medida no puede ser negativo");
        }
    }

    private void validateMedidaPersonal(RegistroMedida registroMedida) {
        validateRegistroMedida(registroMedida);
        if (registroMedida.getIdMedida() <= 0) {
            throw new IllegalArgumentException("El ID de la medida debe ser mayor a 0");
        }
        if (registroMedida.getIdUsuario() == null || registroMedida.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("El ID del usuario es obligatorio para una medida personal");
        }
        if (registroMedida.getIdConsulta() != null) {
            throw new IllegalArgumentException("El ID de consulta debe ser nulo para una medida personal");
        }
    }
}
