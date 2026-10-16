package org.v.anonimizador;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.io.*;

@Path("/anonimizador")
public class PdfAnonymizerResource {

    private static final Logger LOG = Logger.getLogger(PdfAnonymizerResource.class);

    @POST
    @Path("/redact")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM, "application/pdf"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response redact(@QueryParam("safe") @DefaultValue("true") boolean safe,
                           InputStream uploadedPdf) {
        try {
            byte[] result = PdfRedactor.redact(uploadedPdf, safe);
            return Response.ok(new ByteArrayInputStream(result))
                    .header("Content-Disposition", "attachment; filename=\"anonimizado.pdf\"")
                    .build();
        } catch (Exception e) {
            LOG.error("Falha ao anonimizar o PDF", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao anonimizar: " + e.getMessage())
                    .build();
        }
    }
}