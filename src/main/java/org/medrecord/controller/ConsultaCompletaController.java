package org.medrecord.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.medrecord.service.ConsultaCompletaService;
import org.medrecord.dto.ConsultaMedicaDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ConsultaCompletaController {
    private final ConsultaCompletaService consultaCompletaService;

    public ConsultaCompletaController(ConsultaCompletaService consultaCompletaService) {
        this.consultaCompletaService = consultaCompletaService;
    }

    public void getConsultasByUsuario(Context ctx) {
        try {
            int idUsuario = Integer.parseInt(ctx.pathParam("id"));
            List<ConsultaMedicaDTO> consultas = consultaCompletaService.getConsultasByUsuario(idUsuario);
            ctx.json(consultas);
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de usuario inválido");
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result(e.getMessage());
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error al obtener consultas del usuario");
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error interno del servidor");
        }
    }

    public void getConsultaCompleta(Context ctx) {
        try {
            int idConsulta = Integer.parseInt(ctx.pathParam("id"));
            ConsultaMedicaDTO consultaCompleta = consultaCompletaService.getConsultaCompleta(idConsulta);
            if (consultaCompleta != null) {
                ctx.json(consultaCompleta);
            } else {
                ctx.status(HttpStatus.NOT_FOUND).result("Consulta no encontrada");
            }
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de consulta inválido");
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result(e.getMessage());
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error al obtener la consulta");
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error interno del servidor");
        }
    }

    public void createConsultaCompleta(Context ctx) {
        try {
            Map<String, Object> requestBody = ctx.bodyAsClass(Map.class);

            // Delegar toda la lógica al servicio
            int idConsulta = consultaCompletaService.createConsultaCompletaFromRequest(requestBody);

            ctx.status(HttpStatus.CREATED).json(Map.of(
                    "mensaje", "Consulta creada exitosamente",
                    "idConsulta", idConsulta
            ));

        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result(e.getMessage());
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error al crear la consulta");
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error en el formato de los datos enviados");
        }
    }

    public void updateConsultaCompleta(Context ctx) {
        try {
            int idConsulta = Integer.parseInt(ctx.pathParam("id"));
            Map<String, Object> requestBody = ctx.bodyAsClass(Map.class);

            // Delegar toda la lógica al servicio
            int idConsultaActualizada = consultaCompletaService.updateConsultaCompletaFromRequest(idConsulta, requestBody);

            ctx.status(HttpStatus.OK).json(Map.of(
                    "mensaje", "Consulta actualizada exitosamente",
                    "idConsulta", idConsultaActualizada
            ));

        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de consulta inválido");
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result(e.getMessage());
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error al actualizar la consulta");
        } catch (Exception e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Error en el formato de los datos enviados");
        }
    }

    public void deleteConsulta(Context ctx) {
        try {
            int idConsulta = Integer.parseInt(ctx.pathParam("id"));
            consultaCompletaService.deleteConsulta(idConsulta);
            ctx.status(HttpStatus.OK).json(Map.of(
                    "mensaje", "Consulta eliminada exitosamente",
                    "idConsultaEliminada", idConsulta
            ));
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de consulta inválido");
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result(e.getMessage());
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error al eliminar la consulta");
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error interno del servidor");
        }
    }

    public void deletePrescripcion(Context ctx) {
        try {
            int idPrescripcion = Integer.parseInt(ctx.pathParam("id"));
            consultaCompletaService.deletePrescripcion(idPrescripcion);
            ctx.status(HttpStatus.OK).json(Map.of(
                    "mensaje", "Prescripción eliminada exitosamente",
                    "idPrescripcionEliminada", idPrescripcion
            ));
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de prescripción inválido");
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result(e.getMessage());
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error al eliminar la prescripción");
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error interno del servidor");
        }
    }

    public void deleteRegistroMedida(Context ctx) {
        try {
            int idRegistroMedida = Integer.parseInt(ctx.pathParam("id"));
            consultaCompletaService.deleteRegistroMedida(idRegistroMedida);
            ctx.status(HttpStatus.OK).json(Map.of(
                    "mensaje", "Registro de medida eliminado exitosamente",
                    "idRegistroMedidaEliminado", idRegistroMedida
            ));
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de registro de medida inválido");
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result(e.getMessage());
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error al eliminar el registro de medida");
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error interno del servidor");
        }
    }

    public void deleteRegistroProcedimiento(Context ctx) {
        try {
            int idRegistroProcedimiento = Integer.parseInt(ctx.pathParam("id"));
            consultaCompletaService.deleteRegistroProcedimiento(idRegistroProcedimiento);
            ctx.status(HttpStatus.OK).json(Map.of(
                    "mensaje", "Registro de procedimiento eliminado exitosamente",
                    "idRegistroProcedimientoEliminado", idRegistroProcedimiento
            ));
        } catch (NumberFormatException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result("ID de registro de procedimiento inválido");
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST).result(e.getMessage());
        } catch (SQLException e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error al eliminar el registro de procedimiento");
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Error interno del servidor");
        }
    }
}