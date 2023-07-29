import { Component, OnInit, ElementRef } from '@angular/core';
import * as L from 'leaflet';
import 'leaflet-routing-machine';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  private map!: L.Map;
  private routingControl!: L.Routing.Control; // To store the routing control

  private readonly defaultLat = 48.2144935;
  private readonly defaultLng = 16.3760585;
  private readonly defaultZoom = 13;

  constructor(private elementRef: ElementRef) { }

  isColorblindModeActive = true;

  ngOnInit(): void {
    this.initMap();
  }

  private initMap(): void {
    // Create the map
    this.map = L.map(this.elementRef.nativeElement.querySelector('#map')).setView([this.defaultLat, this.defaultLng], this.defaultZoom);
    // Add OpenStreetMap tile layer
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors'
    }).addTo(this.map);
  }

  // Function to create the routing between two points
  createRouting(start: L.LatLng, end: L.LatLng): void {
    if (this.routingControl) {
      this.map.removeControl(this.routingControl);
    }

    // Custom icons for markers
    const startIcon = L.icon({
      iconUrl: 'assets/marker-icon.png',
      iconSize: [32, 32],
      iconAnchor: [16, 32],
      popupAnchor: [0, -32]
    });

    const startCoord = L.latLng([start.lat, start.lng]);
    const endCoord = L.latLng([end.lat, end.lng]);

    // Add markers for start and end points with custom icons
    //L.marker(start, { icon: startIcon, interactive: false }).addTo(this.map).bindPopup('Start Point');
    //L.marker(end, { icon: startIcon, interactive: false }).addTo(this.map).bindPopup('End Point');

    this.routingControl = L.Routing.control({
      waypoints: [startCoord, endCoord],
      itineraryClassName: "oida",
      routeWhileDragging: true // Enable real-time route updates while dragging waypoints
    }).addTo(this.map);

    L.Routing.control({
      waypoints: [endCoord, startCoord],
      itineraryClassName: "oida",

      routeWhileDragging: true // Enable real-time route updates while dragging waypoints
    }).addTo(this.map);

    L.Routing.control({
      waypoints: [L.latLng(47.9988284, 16.856114), L.latLng(48.3772609, 16.3218755)],
      itineraryClassName: "oida",
      lineOptions: {
        styles: [{color: 'green', opacity: 0.8, weight: 2}],
        extendToWaypoints: true,
        missingRouteTolerance: 0
      },
      routeWhileDragging: true // Enable real-time route updates while dragging waypoints
    }).addTo(this.map);
  }



  onRouteButtonClick(): void {
    const startPoint = L.latLng(48.2144935, 16.3760585); // Example start point coordinates
    const endPoint = L.latLng(48.40178485, 15.984785550000007);   // Example end point coordinates

    this.createRouting(startPoint, endPoint);
  }

  // Function to toggle colorblind-friendly mode
  toggleColorblind(): void {
   console.log('test');
   
    this.isColorblindModeActive = !this.isColorblindModeActive;
    if (this.isColorblindModeActive) {
      document.documentElement.classList.add('colorblind-mode');
    } else {
      document.documentElement.classList.remove('colorblind-mode');
    }
  }
}
