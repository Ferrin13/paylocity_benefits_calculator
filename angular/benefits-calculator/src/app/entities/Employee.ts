import {Dependent} from "./Dependent";
import {Entity} from "./Entity";

export class Employee extends Entity{
  firstName: string;
  lastName: string;
  dependents: Dependent[];
}

