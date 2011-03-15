package com.cowlark.stellation3.common.database.values;

import com.cowlark.stellation3.common.database.Reader;
import com.cowlark.stellation3.common.game.Game;
import com.cowlark.stellation3.common.model.SObject;

public class OBJECT extends DATUM
{
	private int _value;
	
	public <T extends SObject> T get()
	{
		return (T) Game.Instance.Database.get(_value);
	}
	
	public int getOid()
	{
		return _value;
	}
	
	@Override
	public void set(Reader r)
	{
		_value = r.readInt();
	}
	
	@Override
	public void set(DATUM d)
	{
		OBJECT o = (OBJECT) d;
		_value = o._value;
	}
}