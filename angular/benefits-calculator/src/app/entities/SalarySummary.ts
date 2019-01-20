export class SalarySummary {
  salary: Map<PayPeriodLength, number>;
  adjustedSalary: Map<PayPeriodLength, number>;
  deductions: Map<PayPeriodLength, number>;

  constructor() {
    this.salary = this.createZeroMap();
    this.adjustedSalary = this.createZeroMap();
    this.deductions = this.createZeroMap();
  }

  createZeroMap(): Map<PayPeriodLength, number> {
    let zeroMap = new Map<PayPeriodLength, number>();
    zeroMap.set(PayPeriodLength.YEARLY, 0);
    zeroMap.set(PayPeriodLength.MONTHLY, 0);
    zeroMap.set(PayPeriodLength.SINGLE, 0);
    return zeroMap;
  }
}

export enum PayPeriodLength {
  YEARLY,
  MONTHLY,
  SINGLE
}
