package org.injustice.powerminer.data;

public enum MinerFactory {

    AL_KHARID(Location.AL_KHARID, Path.AL_KHARID),
    BARB_VILL(Location.BARB_VILL, Path.BARB_VILL),
    LUMBRIDGE_EAST(Location.LUMBRIDGE_SWAMP_EAST, Path.LUMBRIDGE_SWAMP_EAST),
    LUMBRIDGE_WEST(Location.LUMBRIDGE_SWAMP_WEST, Path.LUMBRIDGE_SWAMP_WEST),
    VARROCK_EAST(Location.VARROCK_EAST, Path.VARROCK_EAST),
    VARROCK_WEST(Location.VARROCK_WEST, Path.VARROCK_WEST);
    
    private Location location;
    private Path path;

    MinerFactory(Location location, Path path) {
        this.location = location;
        this.path = path;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public Path getPath() {
        return path;
    }
    
}