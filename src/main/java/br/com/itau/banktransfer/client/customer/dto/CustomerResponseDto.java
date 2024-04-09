package br.com.itau.banktransfer.client.customer.dto;

public record CustomerResponseDto(
        String id,
        String nome,
        String telefone,
        String tipoPessoa,
        Boolean exist
) {
    public CustomerResponseDto(String id, Boolean exist) {
        this(id, null,null,null, exist);
    }

    public CustomerResponseDto {
        if (exist == null)
            exist = true;
    }
}
