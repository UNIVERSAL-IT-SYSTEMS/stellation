rem hydroponicsplant.moo
rem Represents a hydroponics plant.
rem $Source: /cvsroot/stellation/stellation/hydroponicsplant.moo,v $
rem $State: Exp $

.patch hydroponicsplant.moo 6 1
notify(player, "hydroponicsplant.moo");

$god:prop(#0, "hydroponicsplant", create($solarrefinery, $god));
$hydroponicsplant.name = "hydroponics plant";

$hydroponicsplant.asteroids = {0, 1};
$hydroponicsplant.rate = (1.0/24.0);
$hydroponicsplant.production = {0.0, 0.0, 20.0};

$hydroponicsplant.description = "This unit searches out carbonaceous asteroids and builds greenhouse domes around them. In conjunction with sunlight, the plants in the dome draw off the organic compounds in the asteroid to provide the organic consumables required to maintain the biochemical brains in all other units.";

.quit

rem Revision History
rem $Log: hydroponicsplant.moo,v $
rem Revision 1.2  2000/07/30 21:20:19  dtrg
rem Updated all the .patch lines to contain the correct line numbers.
rem Cosmetic makeover; we should now hopefully look marginally better.
rem Bit more work on the nova cannon.
rem A few minor bug fixes.
rem
rem Revision 1.1.1.1  2000/07/29 17:53:01  dtrg
rem Initial checkin.
rem
