package com.alura.Literalura.Service;

public interface IJsonConversor {
    <T> T getData(String json, Class<T> clase);
}
