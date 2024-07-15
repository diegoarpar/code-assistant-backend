package com.co.code.assistant.core.repositories.configurations;


public interface IConfigsRepository <T> {

  T getConfigValue();

  void load();

}
