package com.jnj.codingchallenge.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jnj.codingchallenge.model.Note;
import com.jnj.codingchallenge.model.pk.NotePk;

@Repository
public interface NoteDao extends CrudRepository<Note, NotePk>{
	
}
