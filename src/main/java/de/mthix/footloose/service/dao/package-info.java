/**
 * Data access package for the Footloose service.
 * <p>
 * Entry point and only interface to the service layer are the {@code *Dao}s.
 * <p>
 * These use the package-local mapper interfaces from <em>MyBatis</em>. Since it is a statement- and not an OR-mapper it
 * does not handle nested object hierarchies for write operations; this is done by the {@code *Dao}s - it does however
 * for {@code select}s by way of {@code resultMap}s.
 * <p>
 * The {@code *Dao}s also handle any required bean validation (ingoing and outgoing).
 */
package de.mthix.footloose.service.dao;