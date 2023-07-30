import { Component, OnInit, ElementRef } from '@angular/core';
import * as L from 'leaflet';
import 'leaflet-routing-machine';
import { BackendService } from '../service/backend.service';
import { Route } from '../model/route.model';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
})
export class MapComponent implements OnInit {
  private routes!: Route[];
  private map!: L.Map;
  private routingControl!: L.Routing.Control; // To store the routing control

  private readonly defaultLat = 48.2144935;
  private readonly defaultLng = 16.3760585;
  private readonly defaultZoom = 9;

  constructor(
    private elementRef: ElementRef,
    private backendService: BackendService
  ) {}

  isColorblindModeActive = true;

  ngOnInit(): void {
    this.map = L.map(
      this.elementRef.nativeElement.querySelector('#map')
    ).setView([this.defaultLat, this.defaultLng], this.defaultZoom);
    // Add OpenStreetMap tile layer
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors',
    }).addTo(this.map);
  }

  private initMap(): void {
    // Create the map
    this.map = L.map(
      this.elementRef.nativeElement.querySelector('#map')
    );
    // Add OpenStreetMap tile layer
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors',
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
      popupAnchor: [0, -32],
    });
    // Add markers for start and end points with custom icons
    //L.marker(start, { icon: startIcon, interactive: false }).addTo(this.map).bindPopup('Start Point');
    //L.marker(end, { icon: startIcon, interactive: false }).addTo(this.map).bindPopup('End Point');
  }

  addRouteBasedOnCoordinates(
    map: L.Map,
    targets: L.LatLng[],
    color: string
  ): void {
    L.Routing.control({
      waypoints: targets,
      itineraryClassName: 'oida',
      lineOptions: {
        styles: [{ color: color, opacity: 0.8, weight: 15 }],
        extendToWaypoints: true,
        missingRouteTolerance: 0,
      },
      routeWhileDragging: true, // Enable real-time route updates while dragging waypoints
    }).addTo(map);
  }

  onRouteButtonClick(): void {
    //this.createRouting(startPoint, endPoint);
    this.map.remove();
    this.initMap();

    this.backendService.getRoutes().subscribe((response) => {
      this.routes = response;
      this.handleResponse(response);
    });
  }

  onOptimizeButtonClick(): void {
    this.map.remove();
    this.initMap();
    if (this.routes.length > 0) {
      this.backendService.optimizeRoutes(this.routes).subscribe((response) => {
        this.handleResponse(response);
      });
    } else {
    }
  }

  handleResponse(response: Route[]): void {
    response.forEach((r) => {
      const targetList: L.LatLng[] = [];
      r.targets.forEach((target) => {
        targetList.push(new L.LatLng(target.latitude, target.longitude));
      });

      this.addRouteBasedOnCoordinates(
        this.map,
        targetList,
        '#' + (0x1000000 + Math.random() * 0xffffff).toString(16).substr(1, 6)
      );
    });
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
