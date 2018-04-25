package ar.edu.undav.semillero.domain.entity;

import org.junit.Assert;
import org.junit.Test;

public class CompanyTest {

    @Test
    public void testCreation() {
        String nombre = "nombre";
        String contacto = "contacto";
        String email = "mail@server.com";
        String comentarios = "comentarios";
        Company company = new Company(nombre, contacto, email, comentarios);
        Assert.assertEquals(nombre, company.getName());
        Assert.assertEquals(contacto, company.getContact());
        Assert.assertEquals(email, company.getEmail());
        Assert.assertEquals(comentarios, company.getComments());
    }

    @Test
    public void testAddInterview() {
        Company company = new Company();
        Assert.assertTrue(company.getInterviews().isEmpty());

        company.addInterview(new Interview());
        Assert.assertEquals(1, company.getInterviews().size());
    }
}
