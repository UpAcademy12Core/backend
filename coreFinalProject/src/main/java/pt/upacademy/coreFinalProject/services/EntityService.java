package pt.upacademy.coreFinalProject.services;

import javax.transaction.Transactional;

import pt.upacademy.coreFinalProject.models.EntityRoot;
import pt.upacademy.coreFinalProject.models.DTOS.EntityDTO;
import pt.upacademy.coreFinalProject.repositories.EntityRepository;

@Transactional
public class EntityService<R extends EntityRepository<E, D>, E extends EntityRoot, D extends EntityDTO> {


}
