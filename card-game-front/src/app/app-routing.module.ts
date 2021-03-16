import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GameplayComponent } from './gameplay/gameplay.component';
import { StartGameComponent } from './start-game/start-game.component';
import { WaitingRoomComponent } from './waiting-room/waiting-room.component';


const routes: Routes = [
  { path: 'waitingroom/:id', component: WaitingRoomComponent },
  { path: '', component: StartGameComponent },
  { path: 'gameplay/:id', component: GameplayComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }