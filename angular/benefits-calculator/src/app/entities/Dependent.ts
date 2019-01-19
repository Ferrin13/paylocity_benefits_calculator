import {Entity} from "./Entity";

export class Dependent extends Entity{
  firstName: string;
  lastName: string;
  dependentType: DependentType;
}

export enum DependentType {
  SPOUSE,
  CHILD,
  OTHER
}
