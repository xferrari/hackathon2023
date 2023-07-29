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
  private readonly defaultLat = 37.7749;
  private readonly defaultLng = -122.4194;
  private readonly defaultZoom = 13;
  private routingControl: L.Routing.Control;

  constructor(private elementRef: ElementRef) { }

  ngOnInit(): void {
    this.initMap();
  }

  private initMap(): void {
    require('leaflet-routing-machine');
    // Create the map
    this.map = L.map(this.elementRef.nativeElement.querySelector('#map')).setView([this.defaultLat, this.defaultLng], this.defaultZoom);

    // Add OpenStreetMap tile layer
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors'
    }).addTo(this.map);
  }

  drawRoute() {
    // Replace [LATITUDE1, LONGITUDE1] and [LATITUDE2, LONGITUDE2] with your desired coordinates
    const startPoint = L.latLng([LATITUDE1, LONGITUDE1]);
    const endPoint = L.latLng([LATITUDE2, LONGITUDE2]);

    // Clear previous routing if any
    if (this.routingControl) {
      this.routingControl.setWaypoints([]);
      this.map.removeControl(this.routingControl);
    }

    // Create the routing control and add it to the map
    this.routingControl = L.Routing.control({
      waypoints: [startPoint, endPoint],
      routeWhileDragging: true,
    }).addTo(this.map);
  }

}
