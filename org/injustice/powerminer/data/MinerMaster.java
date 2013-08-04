package org.injustice.powerminer.data;

public enum MinerMaster {

    AL_KHARID(Location.AL_KHARID, Path.AL_KHARID, Bank.AL_KHARID),
    BARB_VILL(Location.BARB_VILL, Path.BARB_VILL, Bank.VARROCK_WEST),
    LUMBRIDGE_EAST(Location.LUMBRIDGE_SWAMP_EAST, Path.LUMBRIDGE_SWAMP_EAST, Bank.AL_KHARID),
    LUMBRIDGE_WEST(Location.LUMBRIDGE_SWAMP_WEST, Path.LUMBRIDGE_SWAMP_WEST, Bank.DRAYNOR),
    VARROCK_EAST(Location.VARROCK_EAST, Path.VARROCK_EAST, Bank.VARROCK_EAST),
    VARROCK_WEST(Location.VARROCK_WEST, Path.VARROCK_WEST, Bank.VARROCK_WEST);
    
    private Location location;
    private Path path;
    private Bank bank;

    MinerMaster(Location location, Path path, Bank bank) {
        this.location = location;
        this.path = path;
        this.bank = bank;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public Path getPath() {
        return path;
    }
    
    public Bank getBank(){
        return bank;
    }
}