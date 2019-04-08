package com.swp.culturehomestay.utils;


import com.swp.culturehomestay.R;

public enum  EnumAmenity {

    BABIES_WELCOME("Babies Welcome",R.drawable.ic_babies_welcome),
    POOL("Pool",R.drawable.ic_pool),
    BALCONY("Balcony",R.drawable.ic_balcony),
    WIFI("Wifi",R.drawable.ic_wifi),
    SHAMPOO("Shampoo",R.drawable.ic_shampoo),
    TOWELS("Towels",R.drawable.ic_towels),
    DRYER("Dryer",R.drawable.ic_dryer),
    TOLLETRIES("Tolletries",R.drawable.ic_tolletries),
    TOOTHPASTE("Toothpaste",R.drawable.ic_toothpaste),
    AIR_CONDITOINING("Air-conditoining",R.drawable.ic_air_conditoining),
    EXTRA_MATTRESS("Extra mattress",R.drawable.ic_extra_mattress),
    NAPKINS("Napkins",R.drawable.ic_napkins),
    SOAP("Soap",R.drawable.ic_soap),
    WASHING_MACHIN("Washing machin",R.drawable.ic_washing_machin),
    MIRERAL_WATER("Mireral water",R.drawable.ic_mireral_water),
    ELEVATOR("Elevator",R.drawable.ic_elevator),
    CENIMATOGRAPH("Cenimatograph",R.drawable.ic_elevator),
    MASSAGE_CHAIR("Massage chair",R.drawable.ic_elevator),
    SMART_TELEVISION("Smart television",R.drawable.ic_smart_television),
    WINE_CABINETS("Wine Cabinets",R.drawable.ic_wine_cabinets),
    OVEN("Oven",R.drawable.ic_oven),
    NO_SMOKING("No smoking",R.drawable.ic_no_smoking),
    MICROWAVE("Microwave",R.drawable.ic_microwave),
    FRIDGE("Fridge",R.drawable.ic_fridge),
    STOVE("Stove",R.drawable.ic_stove),
    PET_WELCOME("Pet welcome",R.drawable.ic_pet_welcome),
    GRILL_BBQ("Grill BBQ",R.drawable.ic_grill_bbq),
    NATUARAL_SURROUND("Natuaral Surround",R.drawable.ic_natuaral_surround),
    GOLF("Golf",R.drawable.ic_golf),
    BEACH_VIEW("Beach View",R.drawable.ic_beach_view),
    FISHING("Fishing",R.drawable.ic_fishing);
    private final int resourceId;
    private final String name;

    EnumAmenity(String name, int resourceId) {
        this.resourceId = resourceId;
        this.name  = name;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getName() {
        return name;
    }

}
