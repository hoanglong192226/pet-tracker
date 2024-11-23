package com.pet.management.tracker.converter;

public interface DtoConverter<E, V> {

  V toDto(E e);

  E fromDto(V viewModel);
}
