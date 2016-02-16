package sample7_mouseclick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 * A simple example application to demonstrate how to handle click on waypoints
 * 
 * @link http://stackoverflow.com/questions/19406947/adding-mouseclicked-event-
 *       listeners-to-waypoints-on-jxmapviewer
 */
public class Sample7 {
    /**
     * @param args
     *            the program args (ignored)
     */
    public static <T> void main(String[] args) {
	JXMapViewer mapViewer = new JXMapViewer();

	// Create a TileFactoryInfo for OpenStreetMap
	TileFactoryInfo info = new OSMTileFactoryInfo();
	DefaultTileFactory tileFactory = new DefaultTileFactory(info);
	tileFactory.setThreadPoolSize(8);
	mapViewer.setTileFactory(tileFactory);

	GeoPosition frankfurt = new GeoPosition(50.11, 8.68);
	// Set the focus
	mapViewer.setZoom(7);
	mapViewer.setAddressLocation(frankfurt);
	// lat="46.10826" long="11.11055">
	// Create a track from the geo-positions
	List<GeoPosition> track = Arrays.asList(frankfurt);

	// Create waypoints from the geo-positions
	Set<Waypoint> waypoints = new HashSet<Waypoint>(
		Arrays.asList(new DefaultWaypoint(frankfurt)));

	// Create a waypoint painter that takes all the waypoints
	WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
	waypointPainter.setWaypoints(waypoints);

	// Create a compound painter that uses both the route-painter and the
	// waypoint-painter
	List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
	painters.add(waypointPainter);

	CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(
		painters);
	mapViewer.setOverlayPainter(painter);

	mapViewer.addMouseListener(new MapMouseListener(mapViewer, waypoints));

	// Display the viewer in a JFrame
	JFrame frame = new JFrame("JXMapviewer2 Example 7");
	frame.getContentPane().add(mapViewer);
	frame.setSize(800, 600);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
    }
}
