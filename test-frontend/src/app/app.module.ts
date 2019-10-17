import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { BeersComponent } from './component/beers/beers.component';
import { BeerService } from './services/beer.service';
import { RouterModule, Routes} from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { FormComponent } from './component/beers/form/form.component';
import { CalculateComponent } from './component/beers/calculate/calculate.component';

const routes: Routes = [
  {path: '', redirectTo: '/beers', pathMatch: 'full'},
  {path: 'beers', component: BeersComponent},
  {path: 'beers/form', component: FormComponent},
  {path: 'beers/form/:id', component: FormComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    BeersComponent,
    FormComponent,
    CalculateComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes)
  ],
  providers: [BeerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
