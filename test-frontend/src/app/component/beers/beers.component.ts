import { Component, OnInit } from '@angular/core';
import { Beer } from '../.././models/beer';
import { BeerService } from '../.././services/beer.service';

@Component({
  selector: 'app-beers',
  templateUrl: './beers.component.html',
  styleUrls: ['./beers.component.css']
})
export class BeersComponent implements OnInit {

  beers: Beer[];
  constructor(private beerService: BeerService) { }

  ngOnInit() {
    this.beerService.getBeers().subscribe(
      beers => this.beers = beers
    );
  }

}
