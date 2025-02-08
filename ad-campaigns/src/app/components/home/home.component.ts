// ad-campaigns/src/app/components/home/home.component.ts
import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from "@angular/router";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    imports: [
        RouterLink,
        RouterLinkActive
    ],
    styleUrls: ['./home.component.scss']
})
export class HomeComponent {}
