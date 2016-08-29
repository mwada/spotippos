package com.vivareal.spotippos.repository.memory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Coordinate;
import com.vivareal.spotippos.model.Position;
import com.vivareal.spotippos.model.Property;
import com.vivareal.spotippos.model.Province;
import com.vivareal.spotippos.repository.TerritoryDao;

@Repository
public class MemoryTerritoryDao implements TerritoryDao {

	private Map<Coordinate, Position> territoryDb;

	public MemoryTerritoryDao() {
		territoryDb = new ConcurrentHashMap<>();
	}

	@Override
	public Set<String> findProvinces(Coordinate coordinate) {
		Position position = territoryDb.getOrDefault(coordinate, new Position());
		return position.getProvinceNames();
	}

	@Override
	public void addProperty(Coordinate coordinate, Long id) {
		Position position = territoryDb.getOrDefault(coordinate, new Position(coordinate));
		position.addProperty(id);
		territoryDb.put(coordinate, position);
	}

	@Override
	public Set<Long> findProperties(Boundaries boundaries) {
		Set<Long> ids = new HashSet<>();
		for (Coordinate coordinate : boundaries.getCoordinates()) {
			Position position = territoryDb.getOrDefault(coordinate, new Position());
			ids.addAll(position.getPropertyIds());
		}
		return ids;
	}

	protected List<Property> loadTerritory(List<Province> provinces, List<Property> properties) {
		for (Province province : provinces) {
			for (Coordinate coordinate : province.getBoundaries().getCoordinates()) {
				addProvince(coordinate, province.getName());
			}
		}
		for (Property property : properties) {
			addProperty(property.getCoordinate(), property.getId());
			property.setProvinces(findProvinces(property.getCoordinate()));
		}
		return properties;
	}
	
	protected void addProvince(Coordinate coordinate, String name) {
		Position position = territoryDb.getOrDefault(coordinate, new Position(coordinate));
		position.addProvince(name);
		territoryDb.put(coordinate, position);
	}

}