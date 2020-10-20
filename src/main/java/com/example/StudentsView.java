package com.example;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("")
public class StudentsView extends VerticalLayout {

    public StudentsView(StudentService service) {
        GridCrud<Student> crud = new GridCrud<>(Student.class, service);
        add(crud);
        setSizeFull();
    }

}
