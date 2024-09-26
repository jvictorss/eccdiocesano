package br.com.verbum.eccdiocesano.domain.services;

import br.com.verbum.eccdiocesano.domain.entities.Usuario;
import br.com.verbum.eccdiocesano.domain.repository.DioceseRepository;
import br.com.verbum.eccdiocesano.domain.repository.ParoquiaRepository;
import br.com.verbum.eccdiocesano.domain.repository.SetorRepository;
import br.com.verbum.eccdiocesano.domain.repository.UsuarioRepository;
import br.com.verbum.eccdiocesano.exception.CantSaveException;
import br.com.verbum.eccdiocesano.exception.PasswordInvalidException;
import br.com.verbum.eccdiocesano.exception.UserNotFoundException;
import br.com.verbum.eccdiocesano.rest.dtos.UsuarioDto;
import br.com.verbum.eccdiocesano.rest.mappers.UsuarioMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static br.com.verbum.eccdiocesano.constants.Constantes.ERROR_SAVE_USER;
import static br.com.verbum.eccdiocesano.constants.Constantes.ERROR_USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final ParoquiaRepository paroquiaRepository;
    private final SetorRepository setorRepository;
    private final DioceseRepository dioceseRepository;
    private final UsuarioMapper usuarioMapper;
    private final ModelMapper mapper;

    public Void createUsuario(UsuarioDto usuarioDto) throws CantSaveException, UserNotFoundException, PasswordInvalidException {
        Optional<Usuario> usuario = pesquisarEmail(usuarioDto.getEmail());
        if (usuario.isPresent()) {
            throw new CantSaveException("Usuário já cadastrado.");
        }

        if (!validatePassword(usuarioDto.getSenha())) {
            throw new PasswordInvalidException("Senha inválida.");
        }

        var mappedUsuario = usuarioMapper.mapToEntity(usuarioDto);
        mappedUsuario.setParoquia(paroquiaRepository.findById(usuarioDto.getParoquiaId()).orElseThrow());
        mappedUsuario.setSetor(setorRepository.findById(usuarioDto.getSetorialId()).orElseThrow());
        mappedUsuario.setDiocese(dioceseRepository.findById(usuarioDto.getDioceseId()).orElseThrow());
        mappedUsuario.setCreatedAt(OffsetDateTime.now());
        mappedUsuario.setSenha(new BCryptPasswordEncoder().encode(usuarioDto.getSenha()));
        mappedUsuario.setIsActive(true);

        try {
            repository.save(mappedUsuario);
        } catch (Exception e) {
            throw new CantSaveException(ERROR_SAVE_USER);
        }
        return null;
    }

    Optional<Usuario> pesquisarEmail(String email) throws UserNotFoundException {
        try {
            return repository.findByEmail(email);
        } catch (Exception ex) {
            throw new UserNotFoundException(ERROR_USER_NOT_FOUND);
        }
    }

    private static boolean validatePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*]).+$";

        if (!password.matches(pattern)) {
            return false;
        }
        String encryptedPassword = passwordEncoder.encode(password);
        return passwordEncoder.matches(password, encryptedPassword);
    }

    public List<UsuarioDto> findAll(boolean isActive) {
        var usuarios = repository.findAllByIsActive(isActive);
        return usuarioMapper.mapToDto(usuarios);
    }
}
