package com.example.demo.dto;


import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FormDao {

    public List<FormDTO> findAll(){
        List<FormDTO> forms = new ArrayList<>();
        List<AttributeDTO> attributeDTOList1 = new ArrayList<>();
        List<AttributeDTO> attributeDTOList2 = new ArrayList<>();
        attributeDTOList1.add(new AttributeDTO(1,"Nazwa firmy: ",11,"TXT","Comarch"));
        attributeDTOList1.add(new AttributeDTO(2,"Branza: ",12,"TXT","IT"));
        attributeDTOList1.add(new AttributeDTO(3,"Adres: ",15,"TXT","Kraków"));
        attributeDTOList2.add(new AttributeDTO(3,"NIP: ",13,"Number","345271345"));
        attributeDTOList2.add(new AttributeDTO(4,"Kraj: ",14,"TXT","Polska"));
        forms.add(new FormDTO(1,"Formularz 1", "version 1",  attributeDTOList1));
        forms.add(new FormDTO(2,"Formularz 2","version 2", attributeDTOList2));

        return forms;
}
}
