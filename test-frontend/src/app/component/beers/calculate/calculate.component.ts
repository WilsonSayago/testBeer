import { Component, OnInit, Input } from '@angular/core';
import { BeerBox } from '../../.././models/beerbox';
import { Beer } from '../../.././models/beer';
import { BeerService } from '../../.././services/beer.service';
import swal from'sweetalert2';

@Component({
  selector: 'app-calculate',
  templateUrl: './calculate.component.html',
  styleUrls: ['./calculate.component.css']
})
export class CalculateComponent implements OnInit {

  @Input()
  private beer : Beer;
  private beerBox : BeerBox = new BeerBox();
  constructor(private beerService: BeerService) {
  }

  ngOnInit() {
  }

  calculate(): void{
    if (this.beerBox.currency == "" || this.beerBox.quantity == null) {
      swal.fire({
        title: 'Alert!',
        text: `currency y/o quantity can´t be null`,
        type: 'error',
        confirmButtonText: 'accept'
      })
    }
    this.beerService.calculate(this.beerBox, this.beer.id).subscribe( response => {
      swal.fire({
        title: 'Success!',
        text: `Total: ${response.beerBox.total}`,
        type: 'success',
        confirmButtonText: 'accept'
      })
      this.beerBox.total = response.beerBox.total;
    }, error => {
      swal.fire({
        title: 'Error!',
        text: `${error.message}`,
        type: 'error',
        confirmButtonText: 'accept'
      })
    });
  }

}
