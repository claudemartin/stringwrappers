# WARNING
This is unfinished work. Not everything is implemented and much more tests are needed. And in most cases the use of `String.intern()` is much simpler and better than this approach with wrappers.

# Description
By default Java creates new String for every String operation. Substrings are newly allocated Strings. This is the best approach in most cases. Sometimes it could be helpful to have just a wrapper of the original String. My project allows just that. It's mostly for educational purposes. In practice this might actually lead to more memory consumption because each wrapper contains a hard reference to the source string, which then can't be garbage collected.

# Operations
 * Substrings
 * Concatenation
 * Reversed Strings
 * To Lower/Upper Case
 * Character Mapping
 * Rotate 13
 * L33tSp34k
 * Endless String
 
