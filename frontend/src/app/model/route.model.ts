import { Loc } from './location.model';

export interface Route {
  targets: [Loc];
  costs: number;
}
