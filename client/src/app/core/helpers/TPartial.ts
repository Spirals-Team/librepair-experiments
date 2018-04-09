export type TPartial<T> = {
  [P in keyof T]?: TPartial<T[P]>;
}
