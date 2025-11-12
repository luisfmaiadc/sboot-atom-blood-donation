package com.doeaqui.sboot_doe_aqui_monolith.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "Hemocentro")
public class EnderecoHemocentro extends Endereco {
}