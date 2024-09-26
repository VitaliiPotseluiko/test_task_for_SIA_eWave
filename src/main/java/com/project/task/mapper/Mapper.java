package com.project.task.mapper;

public interface Mapper<D, M, R> {
    D toDto(M model);

    M toModel(R requestDto);
}
