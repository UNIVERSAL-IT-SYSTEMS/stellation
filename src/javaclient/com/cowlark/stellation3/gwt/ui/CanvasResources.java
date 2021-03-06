package com.cowlark.stellation3.gwt.ui;

import com.cowlark.stellation3.common.game.CompletionListener;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class CanvasResources
{
	public static ImageElement Background;
	public static ImageElement[] Star;
	public static ImageElement Reload;
	
	private static int _loaded;
	private static CompletionListener _listener;
	
	private static ImageResource[] _resources =
	{
		UIResources.Instance.galaxy(),
		UIResources.Instance.star1(),
		UIResources.Instance.star2(),
		UIResources.Instance.star3(),
		UIResources.Instance.star4(),
		UIResources.Instance.star5(),
		UIResources.Instance.star6(),
		UIResources.Instance.star7(),
		UIResources.Instance.star8(),
		UIResources.Instance.star9(),
		UIResources.Instance.reload()
	};
	
	static
	{
		final Image[] images = new Image[_resources.length];
		
		_loaded = 0;
		LoadHandler handler = new LoadHandler()
		{
			@Override
			public void onLoad(LoadEvent event)
			{
				_loaded++;
				
				if (_loaded == _resources.length)
				{
					Background = ImageElement.as(images[0].getElement());
					
					Star = new ImageElement[9];
					for (int i=0; i<9; i++)
						Star[i] = ImageElement.as(images[1+i].getElement());
					
					Reload = ImageElement.as(images[10].getElement());
					
					if (_listener != null)
						_listener.onCompletion();
					
					for (int i=0; i<images.length; i++)
						images[i].removeFromParent();
				}
			}
		};
		
		for (int i=0; i<_resources.length; i++)
		{
			Image image = new Image();
			image.addLoadHandler(handler);
			image.setUrl(_resources[i].getURL());
			image.setVisible(false);
			RootPanel.get().add(image);
			images[i] = image;
		}
	}
	
	public static void waitForLoad(CompletionListener listener)
	{
		if (_loaded == _resources.length)
			listener.onCompletion();
		_listener = listener;
	}
}
