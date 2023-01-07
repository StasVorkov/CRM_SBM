package com.ws.crm.util.converter;

import java.util.List;

public interface DTOConvertable <T,E,B> {

    List<T> convertListToDTO(List<E> eList,B b);

    T convertToDTO(E e,B b);

    E convertToEntity(T t);

}
