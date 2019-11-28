package de.mthix.footloose.service.dao;

import de.mthix.footloose.model.Sighting;
import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.executor.BatchResult;

import java.util.List;

@Mapper
interface SightingMapper {

  int createSighting(Sighting sighting);

  Sighting getSighting(long id);

  List<Sighting> getSightings();

  int getSightingsCount();

  @Flush
  List<BatchResult> flush();
}



