import { Component, OnInit } from '@angular/core';
import { Beer } from '../../.././models/beer';
import { BeerService } from '../../.././services/beer.service';
import {Router, ActivatedRoute} from '@angular/router';
import swal from'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {
  private beer: Beer = new Beer();
  private titulo: string;
  private mostrarCalculate: boolean = false;
  constructor(
    private beerService: BeerService,
    private router: Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.getBeer();
  }

  getBeer(): void{
    this.activatedRoute.params.subscribe(params => {
      let id = params['id']
      if(id){
        this.beerService.getBeer(id).subscribe( (response) => {
          this.beer = response.beerItem;
        }, error => {
          var mensaje = "";
          for (var i in error.error.error) {
             mensaje += error.error.error[i] + '. ';
          }
          swal.fire({
            title: 'Error!',
            text: `${mensaje}`,
            type: 'error',
            confirmButtonText: 'back'
          })
          this.router.navigate(['/beers']);
        })
        this.titulo = "Show Beer";
        this.mostrarCalculate = true;
      } else {
        this.titulo = "Create Beer";
        this.mostrarCalculate = false;
      }
    })
  }

  create(): void {
    this.beerService.create(this.beer)
      .subscribe(response => {
        swal.fire({
          title: 'Created!',
          text: `${response.message}`,
          type: 'success',
          confirmButtonText: 'back'
        })
        this.router.navigate(['/beers'])
      },
    error => {
      console.log(error.errors);
      var mensaje = "";
      for (var i in error.error.errors) {
         mensaje += error.error.errors[i] + ". ";
      }
      swal.fire({
        title: 'Error!',
        text: `${mensaje}`,
        type: 'error',
        confirmButtonText: 'back'
      })
    });
  }
}
