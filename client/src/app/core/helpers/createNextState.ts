import { TPartial } from './TPartial';

export const createNextState = <T>(obj: T, ...assignments: TPartial<T>[]): T => {
  return Object.assign({}, obj, ...assignments);
};
