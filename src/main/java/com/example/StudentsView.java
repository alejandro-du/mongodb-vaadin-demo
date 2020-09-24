package com.example;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("")
public class StudentsView extends VerticalLayout {

    public StudentsView(StudentRepository studentRepository) {
        GridCrud<Student> crud = new GridCrud<>(Student.class);
        crud.setFindAllOperation(studentRepository::findAll);
        crud.setAddOperation(studentRepository::save);
        crud.setUpdateOperation(studentRepository::save);
        crud.setDeleteOperation(studentRepository::delete);
        crud.setSizeFull();

        add(crud);
        setSizeFull();
    }

}
