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

  private readonly defaultLat = 37.7749;
  private readonly defaultLng = -122.4194;
  private readonly defaultZoom = 13;

  constructor(private elementRef: ElementRef) { }

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

    this.routingControl = L.Routing.control({
      waypoints: [
        L.latLng(start.lat, start.lng),
        L.latLng(end.lat, end.lng)
      ],
      routeWhileDragging: true // Enable real-time route updates while dragging waypoints
    }).addTo(this.map);
  }

  onRouteButtonClick(): void {
    const startPoint = L.latLng(48.2144935, 16.3760585); // Example start point coordinates
    const endPoint = L.latLng(48.40178485, 15.984785550000007);   // Example end point coordinates

    this.createRouting(startPoint, endPoint);
  }
}
